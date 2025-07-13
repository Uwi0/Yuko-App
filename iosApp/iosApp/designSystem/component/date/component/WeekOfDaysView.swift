import SwiftUI

struct WeekOfDaysView: View {
	
	let week: WeekValue
	let onSelectedDayOfWeek: (Date) -> Void
	
	var body: some View {
		HStack {
			ForEach(0..<7) { index in
				WeekItemView(weekValue: week, index: index)
					.onTapGesture {
						onSelectedDayOfWeek(week.date[index])
					}
			}
		}
		.frame(width: UIScreen.main.bounds.width)
		.background(
			Rectangle()
				.fill(.white)
		)
	}
}
