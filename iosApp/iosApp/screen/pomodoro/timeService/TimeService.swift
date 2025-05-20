import Foundation
import Combine

final class TimerService: TimerServiceProtocol, ObservableObject {
    @Published var remainingTime: Int = 0
    @Published var isActive: Bool = false
    var remainingTimePublisher: Published<Int>.Publisher {
        $remainingTime
    }
    var isActivePublihsher: Published<Bool>.Publisher {
        $isActive
    }
    
    private var timerCancellable: AnyCancellable?
    private var onTickCallBack: ((Int)-> Void)?
    private var onFinishCallBack: (() -> Void)?
    
    func startTimer(interval: TimeInterval = 1, onTick: @escaping (Int) -> Void, onFinish: @escaping () -> Void) {
        isActive = true
        self.onTickCallBack = onTick
        self.onFinishCallBack = onFinish
        
        timerCancellable = Timer.publish(every: interval, on: .main, in: .common)
            .autoconnect()
            .sink { [weak self] _ in
                guard let self = self else { return }
                
                if self.remainingTime > 0 {
                    self.remainingTime -= 1
                    self.onTickCallBack?(self.remainingTime)
                } else {
                    self.stopTimer()
                    self.onFinishCallBack?()
                }
            }
    }
    
    func pauseTimer() {
            isActive = false
            timerCancellable?.cancel()
            timerCancellable = nil
        }

        func stopTimer() {
            isActive = false
            timerCancellable?.cancel()
            timerCancellable = nil
        }
}
