import SwiftUI
import Shared

struct WeekOfDaysView: View {
	
	let week: WeekModel
	let onSelectedDayOfWeek: (Date) -> Void
	
	var body: some View {
		HStack {
			ForEach(0..<7) { index in
				WeekItemView(weekValue: week, index: index)
					.onTapGesture {
						onSelectedDayOfWeek(week.dates[index])
					}
			}
		}
		.frame(maxWidth: .infinity)
		.background(Rectangle().fill(.white))
	}
}
