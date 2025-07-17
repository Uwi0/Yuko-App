import SwiftUI
import Shared

struct MonthsView: View {
	
	let month: MonthModel
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
			ForEach(month.weeks) { weeks in
				WeekDaysBody(weeks: weeks)
					.frame(height: weekHeight)
					.animation(.easeInOut(duration: 0.3), value: month.weeks.count)
			}
		}
		.frame(height: totalCalendarHeight)
	}
	
	
	@ViewBuilder
	private func WeekDaysBody(weeks: WeekOfMonthModel) -> some View {
		HStack {
			ForEach(0..<7) { index in
				let dayState = weeks.days[index]
				DayView(dayState: dayState)
			}
		}
	}
	
	@ViewBuilder
	private func DayView(dayState: DayState) -> some View {
		let text = switch onEnum(of: dayState) {
		case let .day(day): dateToString(date: day.date.toDate(), format: "d")
		case .empty: ""
		}
		
		Text(text)
			.frame(maxWidth: .infinity)
	}
	
}
