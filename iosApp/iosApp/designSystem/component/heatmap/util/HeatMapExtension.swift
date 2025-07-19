import Foundation
import Shared

extension CompletionDayModel {
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
