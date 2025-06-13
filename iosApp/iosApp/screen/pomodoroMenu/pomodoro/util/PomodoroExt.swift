import Foundation
import Shared

extension Int {
    func toFormatMinutesAndSeconds() -> String {
        let seconds = self
        let minutes = seconds / 60
        let secondsPart = seconds % 60
        return String(format: "%02d:%02d", minutes, secondsPart)
    }
}

extension SessionType {
	func asTitle(countDown: String) -> String {
		switch self {
		case .start: "Start"
		case .cancel: "Cancel \(countDown)"
		case .giveUp: "Give Up"
		case .breakTime: "Skip Break Time"
		}
	}
}
