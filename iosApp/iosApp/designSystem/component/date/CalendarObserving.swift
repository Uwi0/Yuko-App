import Foundation

public protocol CalendarObserving: AnyObject {
	func didTapDay(onDate: Date)
	func dayAppeared(forDate: Date)
	func didSetInitialHeight(_ height: CGFloat)
	func didAutoSelectInitialDay(_ date: Date)
}

public extension CalendarObserving {
		func dayAppeared(forDate: Date) {
				
		}

		func didSetInitialHeight(_ height: CGFloat) {
				
		}

		func didAutoSelectInitialDay(_ date: Date) {
				
		}
}
