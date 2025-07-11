import Foundation

struct CalendarDayModel: Hashable {
	let date: Date
	var isSelected: Bool
	var hasContentAvailable: Bool
	let isAvailable: Bool
}
