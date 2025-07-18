import SwiftUI
import Shared

struct CompletionHeatMapView: View {
	let completionYear: CompletionYearModel
	@State private var selectedDay: CompletionDayModel?
	@State private var showTollTip: Bool = false
	
	private let daySize: CGFloat = 12
	private let daySpacing: CGFloat = 2
	private let monthLabels = MonthLabels.shared.labels
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
		VStack(alignment: .leading, spacing: 0) {
			MonthLabelsView()
			ScrollView {
				HStack(alignment: .top, spacing: 0) {
					WeekDayLabelsView()
					CompletionGridView()
				}
			}
			
		}
	}
	
	
	@ViewBuilder
	private func MonthLabelsView() -> some View {
		HStack {
			Rectangle()
				.fill(ColorTheme.primary)
				.frame(width: 20, height: 16)
			
			ForEach(0..<12, id: \.self) { index in
				let monthWidth = calculateMonthWidthBy(index: index)
				
				if monthWidth > 0 {
					Text(monthLabels[index])
						.font(Typography.titleMedium)
						.foregroundStyle(ColorTheme.outline)
						.frame(width: monthWidth, alignment: .leading)
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
					.frame(width: 18, height: daySize, alignment: .trailing)
			}
		}
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
	
	private func calculateMonthWidthBy(index: Int) -> CGFloat {
		let weekInMonth: CGFloat = 4.3
		return weekInMonth * (daySize + daySpacing)
	}
}

#Preview {
	let dummyCompletion = CompletionYearStore()
	let dummyData = dummyCompletion.generateCompletionYear(year: 2022, completionData: [:])
	CompletionHeatMapView(completionYear: dummyData)
}
