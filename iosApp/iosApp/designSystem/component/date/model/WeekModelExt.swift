import Foundation
import Shared

extension WeekModel: @retroactive Identifiable {
	public var id: Int { Int(self.weekId) }
	public var dates: [Date] { self.weekDates.map { $0.toDate() }}
}
