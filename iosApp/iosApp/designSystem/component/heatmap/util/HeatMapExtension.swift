import Foundation
import Shared

extension CompletionDayModel.Day {
	public var date: Date {
		self.dateKt.toDate()
	}
	public var level: Int {
		Int(self.levelKt)
	}
}

extension MonthInfoModel {
	public var monthIndex: Int {
		Int(self.monthIndexKt)
	}
	public var width: CGFloat {
		CGFloat(self.widthKt)
	}
}

extension CompletionYearModel {
	public var year: String {
		self.yearKt.removeThousandSeparator()
	}
	public var totalCompletions: String {
		self.totalCompletionsKt.removeThousandSeparator()
	}
}
