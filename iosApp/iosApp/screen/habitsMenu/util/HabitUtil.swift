import Foundation
import Shared

extension CompletionViewMode {
	func toIcon() -> String {
		switch self {
		case .weekly: AssetIconApp.displayDateWeekly
		case .monthly: AssetIconApp.displayDateMonthly
		case .yearly: AssetIconApp.displayDateYearly
		}
	}
}
