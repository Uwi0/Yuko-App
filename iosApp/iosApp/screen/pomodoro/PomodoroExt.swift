import Foundation

extension Int {
    func toFormatMinutesAndSeconds() -> String {
        let seconds = self
        let minutes = seconds / 60
        let secondsPart = seconds % 60
        return String(format: "%02d:%02d", minutes, secondsPart)
    }
}
