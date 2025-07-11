import Foundation

extension Date {
		static func dates(from fromDate: Date, to toDate: Date) -> [Date] {
				var dates: [Date] = []
				var date = fromDate
				
				while date <= toDate {
						let startOfDate = Calendar.current.startOfDay(for: date)
						dates.append(startOfDate)
						guard let newDate = Calendar.current.date(byAdding: .day, value: 1, to: date) else { break }
						date = newDate
				}
				return dates
		}

		var weekday: Int {
				(Calendar.current.component(.weekday, from: self) - Calendar.current.firstWeekday + 7) % 7 + 1
		}
}
