import Foundation
import Combine
import AVFoundation
import UIKit


final class BackgroundTimerService: TimerServiceProtocol, ObservableObject {
        
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
    
    private var audioPlayer: AVAudioPlayer?
    private var backgroundTask: UIBackgroundTaskIdentifier = .invalid
    
    private var backgroundEntryTime: Date?
    
    init() {
        
    }
    
    private func setupNotification() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(appDidEnterBackground),
            name: UIScene.willDeactivateNotification,
            object: nil
        )
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(appWillEnterForeground),
            name: UIScene.didActivateNotification,
            object: nil
        )
    }
    
    @objc private func appDidEnterBackground() {
        if isActive {
            backgroundTask = UIApplication.shared.beginBackgroundTask(withName: "TimeBackgroundExecution") { [weak self] in
                self?.endBackgroundTask()
            }
            backgroundEntryTime = Date()
            startSilendAudio()
        }
    }
    
    @objc private func appWillEnterForeground() {
        stopSilentAudio()
        endBackgroundTask()
        
        if isActive, let entryTime = backgroundEntryTime {
            let timeInBackground = Date().timeIntervalSince(entryTime)
            let estimateRemainingTime = remainingTime - Int(timeInBackground)
            
            if estimateRemainingTime <= 0 {
                remainingTime = 0
                stopTimer()
                onFinishCallback?()
            } else {
                remainingTime = estimateRemainingTime
                startTimer(initialTime: remainingTime, interval: 1, onTick: onTickCallback!, onFinish: onFinishCallback!)
            }
        }
        
        backgroundEntryTime = nil
    }
    
    private func startSilendAudio() {
        do {
            try AVAudioSession.sharedInstance().setCategory(.playback, mode: .default, options: [.mixWithOthers, .allowAirPlay])
            try AVAudioSession.sharedInstance().setActive(true)
            
            guard let path = Bundle.main.url(forResource: "silent", withExtension: "mp3", subdirectory: "sound") else { return }
            audioPlayer = try AVAudioPlayer(contentsOf: path)
            audioPlayer?.numberOfLoops = -1
            audioPlayer?.volume = 0.0
            audioPlayer?.prepareToPlay()
            audioPlayer?.play()
            print("start silent audio")
        } catch {
            print("Failed to set up audio session or play silent audio: \(error.localizedDescription)")
        }
    }
    
    private func stopSilentAudio() {
        audioPlayer?.stop()
        audioPlayer = nil
        do {
            try AVAudioSession.sharedInstance().setActive(false, options: .notifyOthersOnDeactivation)
        }catch {
            print("Failed to deactivate audio session: \(error.localizedDescription)")
        }
    }
    
    private func endBackgroundTask() {
        if backgroundTask != .invalid {
            UIApplication.shared.endBackgroundTask(backgroundTask)
            backgroundTask = .invalid
        }
    }
    
    func startTimer(
        initialTime: Int,
        interval: TimeInterval = 1,
        onTick: @escaping (Int) -> Void,
        onFinish: @escaping () -> Void
    ) {
        if isActive {
            stopTimer()
        }
        
        remainingTime = initialTime
        isActive = true
        self.onTickCallback = onTick
        self.onFinishCallback = onFinish
        
        timerCancellable = Timer.publish(every: interval, on: .main, in: .common)
            .autoconnect()
            .sink { [weak self] _ in
                guard let self = self else { return }
                print("countdown: \(self.remainingTime)")
                if self.remainingTime > 0 {
                    self.remainingTime -= 1
                    self.onTickCallback?(self.remainingTime)
                } else {
                    self.stopTimer()
                    self.onFinishCallback?()
                }
            }
        
        print("Timer started. Initial time: \(initialTime)")
    }

    
    func stopTimer() {
        isActive = false
        timerCancellable?.cancel()
        timerCancellable = nil
        remainingTime = 0
        stopSilentAudio()
        endBackgroundTask()
    }
    
    
}
