import SwiftUI
import Shared

struct CompletionWeekView: View {
	
	let week: CompletionWeekModel
	let daySize: CGFloat
	let daySpacing: CGFloat
	let onTapDay: (CompletionDayModel) -> Void
	
	var body: some View {
		VStack(spacing: daySpacing) {
			ForEach(Array(week.days.enumerated()), id: \.offset) { _, completionDay in
				CompletionDayView(
					day: completionDay,
					size: daySize,
					onTap: { onTapDay(completionDay) }
				)
			}
		}
	}
}
