import SwiftUI
import Shared

struct WeekOfDaysView: View {
	
	let week: WeekModel
	
	var body: some View {
		HStack {
			ForEach(0..<7) { index in
				WeekItemView(weekValue: week, index: index)
			}
		}
		.frame(maxWidth: .infinity)
		.background(Rectangle().fill(.white))
	}
}
