import SwiftUI
import Shared

struct MonthsView: View {
	
	let month: MonthModel
	private let weekDays: [String] = WeekDays.shared.shortNames
	
	var body: some View {
		VStack {
			WeekDaysHeader()
//			MonthWeeksBody()
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
		VStack {
			ForEach(month.weeks.indices, id:\.self) { index in
				let weeks = month.weeks[index]
				WeekDaysBody(weeks: weeks)
			}
		}
	}
	
	@ViewBuilder
	private func WeekDaysBody(weeks: WeekModel) -> some View {
		HStack {
			ForEach(0..<7) { index in
				let week = weeks.weekDates[index]
				Text(dateToString(date: week.toDate(), format: "d"))
					.frame(maxWidth: .infinity)
			}
		}
	}
}
