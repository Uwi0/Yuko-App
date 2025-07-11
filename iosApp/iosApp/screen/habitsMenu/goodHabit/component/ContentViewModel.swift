import Foundation

final class ContentViewModel: ObservableObject {
		
		let calendarViewModel: HorizontalCalendarViewModel

		@Published var calendarHeight: CGFloat?

		init() {
				let components = DateComponents(year: 2023, month: 1, day: 1)
				let startDate = Calendar.current.date(from: components)!
				self.calendarViewModel = .init(startDate: startDate)
				calendarViewModel.delegate = self
		}

		func highlightDaysOnCalendar() {
				let threeDaysAgo = Calendar.current.date(byAdding: .day, value: -3, to: Calendar.current.startOfDay(for: .now))!
				let sevenDaysAgo = Calendar.current.date(byAdding: .day, value: -7, to: Calendar.current.startOfDay(for: .now))!
				let tenDaysAgo = Calendar.current.date(byAdding: .day, value: -10, to: Calendar.current.startOfDay(for: .now))!
				calendarViewModel.setContentAvailableForDaysWithGivenDates(
						[
								threeDaysAgo,
								sevenDaysAgo,
								tenDaysAgo
						]
				)
		}
}

extension ContentViewModel: CalendarObserving {
		func didTapDay(onDate: Date) {
				
		}
		
		func dayAppeared(forDate: Date) {
				
		}

		func didSetInitialHeight(_ height: CGFloat) {
				calendarHeight = height
		}
}
