import Foundation
import Shared

extension MonthModel: @retroactive Identifiable {
	public var id: Int { Int(self.monthId) }
}

extension WeekOfMonthModel: @retroactive Identifiable {
	public var id: Int { Int(self.weekId) }
}
