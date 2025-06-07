import Foundation
import ActivityKit
import Combine
import AVFoundation
import UIKit
import Shared

final class BackgroundTimerService: NSObject, TimerServiceProtocol, ObservableObject {
    
    @Published var remainingTime: Int = 0
    @Published var isActive: Bool = false
    var remainingTimePublisher: Published<Int>.Publisher {
        $remainingTime
    }
    var isActivePublihsher: Published<Bool>.Publisher {
        $isActive
    }
    
    private var timerCancellable: AnyCancellable?
    private var onTickCallback: ((Int) -> Void)?
    private var onFinishCallback: (() -> Void)?
    
    private var backgroundTask: UIBackgroundTaskIdentifier = .invalid
    private var backgroundEntryTime: Date?
    private var initialTimerDuration: Int = 0
    private var sessionType: String = SessionType.focus.name
    
    private var currentActivity: Activity<FocusTimerAttributes>?
    
    private let timerFinishedNotificationId = "timer_finished_notification"
    
    override init() {
        super.init()
        setupNotification()
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
        endBackgroundTask()
        stopLiveActivity()
    }
    
    private func setupNotification() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(appDidEnterBackground),
            name: UIApplication.didEnterBackgroundNotification,
            object: nil
        )
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(appWillEnterForeground),
            name: UIApplication.willEnterForegroundNotification,
            object: nil
        )
    }
    
    @objc private func appDidEnterBackground() {
        print("App entering background, timer active: \(isActive)")
        
        if isActive {
            backgroundTask = UIApplication.shared.beginBackgroundTask(withName: "TimerBackgroundExecution") { [weak self] in
                print("Background task about to expire")
                self?.handleBackgroundTaskExpiration()
            }
            
            backgroundEntryTime = Date()
            updateLiveActivity()
        }
    }
    
    @objc private func appWillEnterForeground() {
        print("App entering foreground")
        
        endBackgroundTask()
        
        if isActive, let entryTime = backgroundEntryTime {
            let timeInBackground = Date().timeIntervalSince(entryTime)
            let estimatedRemainingTime = remainingTime - Int(timeInBackground)
            
            print("Time in background: \(timeInBackground)s, estimated remaining: \(estimatedRemainingTime)s")
            
            if estimatedRemainingTime <= 0 {
                remainingTime = 0
                stopTimer()
                showFocusCompletionNotification()
                onFinishCallback?()
            } else {
                remainingTime = estimatedRemainingTime
                updateLiveActivity()
                restartTimerWithRemainingTime(estimatedRemainingTime)
            }
        }
        
        backgroundEntryTime = nil
    }
    
    private func handleBackgroundTaskExpiration() {
        endBackgroundTask()
    }
    
    
    private func startLiveActivity() {
        guard ActivityAuthorizationInfo().areActivitiesEnabled else {
            print("Live Activities not enabled")
            return
        }
        
        let attributes = FocusTimerAttributes(
            startTime: Date(),
            sessionName: sessionType
        )
        
        let contentState = FocusTimerAttributes.ContentState(
            remainingTime: remainingTime,
            totalTime: initialTimerDuration,
            isActive: isActive,
            sessionType: sessionType
        )
        
        let activityContent = ActivityContent(state: contentState, staleDate: nil)
        
        do {
            currentActivity = try Activity.request(
                attributes: attributes,
                content: activityContent,
                pushType: nil
            )
            print("Live Activity started successfully")
        } catch {
            print("Failed to start Live Activity: \(error.localizedDescription)")
        }
    }
    
    private func updateLiveActivity() {
        guard let activity = currentActivity else { return }
        
        let updatedContentState = FocusTimerAttributes.ContentState(
            remainingTime: remainingTime,
            totalTime: initialTimerDuration,
            isActive: isActive,
            sessionType: sessionType
        )
        
        let updatedContent = ActivityContent(
            state: updatedContentState,
            staleDate: Date().addingTimeInterval(TimeInterval(remainingTime + 60))
        )
        
        Task {
            await activity.update(updatedContent)
        }
    }
    
    private func stopLiveActivity() {
        guard let activity = currentActivity else { return }
        
        let finalContentState = FocusTimerAttributes.ContentState(
            remainingTime: 0,
            totalTime: initialTimerDuration,
            isActive: false,
            sessionType: sessionType
        )
        
        let finalContent = ActivityContent(
            state: finalContentState,
            staleDate: Date().addingTimeInterval(30)
        )
        
        Task {
            await activity.end(finalContent, dismissalPolicy: .immediate)
        }
        
        currentActivity = nil
        print("Live Activity stopped")
    }
    
    private func scheduleTimerCompletionNotification(after seconds: Int) {
        let content = UNMutableNotificationContent()
        content.title = "🍅 \(sessionType) Complete!"
        
        let focusTime = formatTime(initialTimerDuration)
        let message = "Great job! You focused for \(focusTime). Time for a break!"
        
        content.body = message
        content.sound = .default
        content.badge = 1
        
        content.userInfo = [
            "type": "timer_completed",
            "focus_duration": initialTimerDuration,
            "session_type": sessionType,
            "completion_time": Date().timeIntervalSince1970
        ]
        
        let trigger = UNTimeIntervalNotificationTrigger(timeInterval: TimeInterval(seconds), repeats: false)
        let request = UNNotificationRequest(
            identifier: timerFinishedNotificationId,
            content: content,
            trigger: trigger
        )
        
        UNUserNotificationCenter.current().add(request) { error in
            if let error = error {
                print("Failed to schedule completion notification: \(error.localizedDescription)")
            } else {
                print("Timer completion notification scheduled for \(seconds) seconds")
            }
        }
    }
    
    private func showFocusCompletionNotification() {
        let content = UNMutableNotificationContent()
        content.title = "🎉 \(sessionType) Session Complete!"
        
        let focusTime = formatTime(initialTimerDuration)
        let message = "Excellent! You stayed focused for \(focusTime). Well done!"
        
        content.body = message
        content.sound = .default
        content.badge = 1
        
        let trigger = UNTimeIntervalNotificationTrigger(timeInterval: 0.1, repeats: false)
        let request = UNNotificationRequest(
            identifier: "focus_completed_foreground",
            content: content,
            trigger: trigger
        )
        
        UNUserNotificationCenter.current().add(request)
    }
    
    private func formatTime(_ seconds: Int) -> String {
        let minutes = seconds / 60
        let remainingSeconds = seconds % 60
        return String(format: "%02d:%02d", minutes, remainingSeconds)
    }
    
    private func restartTimerWithRemainingTime(_ time: Int) {
        timerCancellable?.cancel()
        
        timerCancellable = Timer.publish(every: 1, on: .main, in: .common)
            .autoconnect()
            .sink { [weak self] _ in
                guard let self = self else { return }
                
                if self.remainingTime > 0 {
                    self.remainingTime -= 1
                    self.onTickCallback?(self.remainingTime)
                    
                    
                    self.updateLiveActivity()
                    
                } else {
                    self.stopTimer()
                    self.showFocusCompletionNotification()
                    self.onFinishCallback?()
                }
            }
    }
    
    private func endBackgroundTask() {
        if backgroundTask != .invalid {
            UIApplication.shared.endBackgroundTask(backgroundTask)
            backgroundTask = .invalid
            print("Background task ended")
        }
    }
    
    func startTimer(
        initialTime: Int,
        sessionType: String = SessionType.focus.name,
        interval: TimeInterval = 1,
        onTick: @escaping (Int) -> Void,
        onFinish: @escaping () -> Void
    ) {
        if isActive {
            stopTimer()
        }
        
        remainingTime = initialTime
        initialTimerDuration = initialTime
        self.sessionType = sessionType
        isActive = true
        self.onTickCallback = onTick
        self.onFinishCallback = onFinish
        
        startLiveActivity()
        
        scheduleTimerCompletionNotification(after: initialTime)
        
        timerCancellable = Timer.publish(every: interval, on: .main, in: .common)
            .autoconnect()
            .sink { [weak self] _ in
                guard let self = self else { return }
                
                if self.remainingTime > 0 {
                    self.remainingTime -= 1
                    self.onTickCallback?(self.remainingTime)
                    
                    
                    self.updateLiveActivity()
                    
                } else {
                    self.stopTimer()
                    self.showFocusCompletionNotification()
                    self.onFinishCallback?()
                }
            }
        
        print("Timer started. Initial time: \(initialTime), Session: \(sessionType)")
    }
    
    func stopTimer() {
        isActive = false
        timerCancellable?.cancel()
        timerCancellable = nil
        remainingTime = 0
        initialTimerDuration = 0
        
        stopLiveActivity()
        
        UNUserNotificationCenter.current().removePendingNotificationRequests(withIdentifiers: [timerFinishedNotificationId])
        endBackgroundTask()
        
        print("Timer stopped")
    }
    
}

extension BackgroundTimerService: UNUserNotificationCenterDelegate {
    
    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void
    ) {
        completionHandler([.alert, .sound, .badge])
    }
    
    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        didReceive response: UNNotificationResponse,
        withCompletionHandler completionHandler: @escaping () -> Void
    ) {
        let userInfo = response.notification.request.content.userInfo
        
        if let type = userInfo["type"] as? String,
           type == "timer_completed",
           let focusDuration = userInfo["focus_duration"] as? Int,
           let sessionType = userInfo["session_type"] as? String {
            
            print("User completed \(focusDuration) seconds of \(sessionType) time")
        }
        
        completionHandler()
    }
}
