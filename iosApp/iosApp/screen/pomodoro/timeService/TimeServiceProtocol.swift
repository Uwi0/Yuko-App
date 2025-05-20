import Foundation

protocol TimerServiceProtocol: AnyObject {
    var remainingTimePublisher: Published<Int>.Publisher { get }
    var isActivePublihsher: Published<Bool>.Publisher { get }
    var remainingTime: Int { get set }
    var isActive: Bool { get set }
    
    func startTimer(interval: TimeInterval, onTick: @escaping (Int) -> Void, onFinish: @escaping () -> Void)
    func pauseTimer()
    func stopTimer()
}

