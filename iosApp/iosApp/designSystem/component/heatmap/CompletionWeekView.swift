import SwiftUI
import Shared

struct CompletionWeekView: View {
	
	let week: CompletionWeekModel
	let daySize: CGFloat
	let daySpacing: CGFloat
	let onTapDay: (CompletionDayModel) -> Void
	
	var body: some View {
		VStack(spacing: daySpacing) {
			ForEach(week.days, id: \.date) { day in
				CompletionDayView(
					day: day,
					size: daySize,
					onTap: { onTapDay(day) }
				)
			}
		}
	}
}
