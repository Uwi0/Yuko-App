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
