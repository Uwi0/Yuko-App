import Foundation
import Shared

func dateToString(date: Date,format: String)->String{
	let formatter = DateFormatter()
	formatter.dateFormat = format
	return formatter.string(from: date)
}

extension Kotlinx_datetimeLocalDate {
	func toDate() -> Date {
		var components = DateComponents()
		components.year = Int(self.year)
		components.month = Int(self.month.ordinal) + 1
		components.day = Int(self.day)
		return Calendar.current.date(from: components)!
	}
}
