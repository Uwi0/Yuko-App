import SwiftUI
import Shared

struct CompletionHeatMapView: View {
	let completionYear: CompletionYearModel
	@State private var selectedDay: CompletionDayModel = .Empty()
	@State private var showTollTip: Bool = false
	
	private let daySize: CGFloat = 16
	private let daySpacing: CGFloat = 3
	
	private let weekDayLabels = WeekDays.shared.shortNames
	
	var body: some View {
		VStack(alignment: .leading, spacing: 16) {
			HeaderView()
			HeatMapGridView()
			LegendView()
		}
	}
	
	@ViewBuilder
	private func HeaderView() -> some View {
		HStack {
			Text("\(completionYear.totalCompletions) completions in \(completionYear.year)")
				.font(Typography.titleLarge)
			
			Spacer()
		}
	}
	
	@ViewBuilder
	private func HeatMapGridView() -> some View {
		ScrollView(.horizontal, showsIndicators: false) {
			HStack(alignment: .center, spacing: 4) {
				WeekDayLabelsView()
				VStack(alignment: .leading, spacing: 0) {
					CompletionMonthLabel(
						completionYear: completionYear,
						daySize: daySize,
						daySpacing: daySpacing
					)
					CompletionGridView()
				}
			}
		}
	}
	
	@ViewBuilder
	private func WeekDayLabelsView() -> some View {
		VStack(alignment: .trailing, spacing: daySpacing) {
			ForEach(0..<7, id: \.self) { index in
				let label = (index % 2 == 1) ? weekDayLabels[index] : ""
				Text(label)
					.font(Typography.titleSmall)
					.foregroundStyle(ColorTheme.outline)
					.frame(width: 36, height: daySize, alignment: .trailing)
			}
		}
		.padding(.top, daySize)
	}
	
	@ViewBuilder
	private func CompletionGridView() -> some View {
		HStack(alignment: .top, spacing: daySpacing) {
			ForEach(Array(completionYear.weeks.enumerated()), id: \.offset) { _, week in
				CompletionWeekView(
					week: week,
					daySize: daySize,
					daySpacing: daySpacing,
					onTapDay: { day in
						selectedDay = day
						showTollTip = true
					}
				)
			}
		}
		.overlay(
			ToolTipView(day: selectedDay, isVisible: showTollTip)
				.onTapGesture {
					showTollTip = false
				}
		)
	}
	
}

#Preview {
	let dummyCompletion = CompletionYearStore()
	let dummyData = dummyCompletion.generateCompletionYear(year: 2021, targetFrequency: 1, completionData: [:])
	CompletionHeatMapView(completionYear: dummyData)
}
