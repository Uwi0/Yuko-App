import SwiftUI
import Shared

struct CompletionMonthLabel: View {
	
	let completionYear: CompletionYearModel
	let daySize: CGFloat
	let daySpacing: CGFloat
	
	private let monthLabels = MonthLabels.shared.labels
	
	var body: some View {
		HStack(spacing: 0) {
			Rectangle()
				.fill(Color.clear)
				.frame(width: 20, height: 16)
			
			let monthPositions = completionYear.calculateMonthPositions(
				daySize: Float(daySize),
				daySpacing: Float(daySpacing)
			)
			
			ForEach(Array(monthPositions.enumerated()), id: \.offset) { _, monthInfo in
				if monthInfo.width > 0 {
					Text(monthLabels[monthInfo.monthIndex])
						.font(Typography.titleMedium)
						.foregroundStyle(ColorTheme.outline)
						.frame(width: monthInfo.width, alignment: .leading)
				}
			}
		}
	}
}
