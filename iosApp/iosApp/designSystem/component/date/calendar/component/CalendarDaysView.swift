import SwiftUI
import Shared

struct CalendarDaysView: View {
	
	private let weekDays: [String] = WeekDays.shared.shortNames
	
	var body: some View {
		VStack {
			WeekDaysHeader()
		}
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
		.frame(maxWidth: .infinity)
		.background(Rectangle().fill(.white))
	}
}

#Preview {
	CalendarDaysView()
}
