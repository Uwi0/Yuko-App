import SwiftUI
import Shared

struct MonthsView: View {
	
	let month: MonthModel
	let completionMonths: [[DayValue]]
	private let weekDays: [String] = WeekDays.shared.shortNames
	
	private var totalCalendarHeight: CGFloat { 240 }
	private var weekHeight: CGFloat {
			totalCalendarHeight / 6
	}
	
	var body: some View {
		VStack {
			WeekDaysHeader()
			MonthWeeksBody()
		}
		.frame(maxWidth: .infinity)
		.background(Rectangle().fill(.white))
	}
	
	@ViewBuilder
	private func WeekDaysHeader() -> some View {
		HStack {
			ForEach(weekDays, id: \.self){ day in
				Text(day)
					.font(Typography.titleMedium)
					.frame(maxWidth: .infinity)
			}
		}
	}
	
	@ViewBuilder
	private func MonthWeeksBody() -> some View {
		VStack(spacing: 0) {
			ForEach(Array(month.weeks.enumerated()), id: \.offset) { index, weeks in
				let weekCompletions = completionMonths[index]
				WeekDaysBody(weeks: weeks, weekCompletions: weekCompletions)
					.frame(height: weekHeight)
					.animation(.easeInOut(duration: 0.3), value: month.weeks.count)
			}
		}
		.frame(height: totalCalendarHeight)
	}
	
	
	@ViewBuilder
	private func WeekDaysBody(weeks: WeekOfMonthModel, weekCompletions: [DayValue]) -> some View {
		HStack {
			ForEach(0..<7) { index in
				let dayState = weeks.days[index]
				let dayValue = weekCompletions[index]
				DayView(dayState: dayState, dayValue: dayValue)
			}
		}
	}
	
	@ViewBuilder
	private func DayView(dayState: DayState, dayValue: DayValue) -> some View {
		let text = switch onEnum(of: dayState) {
		case let .day(day): dateToString(date: day.date.toDate(), format: "d")
		case .empty: ""
		}
		
		let color = switch onEnum(of: dayValue) {
		case .day: Color.yellow.opacity(0.5)
		case .empty: Color.white
		}
		
		Text(text)
			.frame(maxWidth: .infinity)
			.background(color)
	}
	
}
