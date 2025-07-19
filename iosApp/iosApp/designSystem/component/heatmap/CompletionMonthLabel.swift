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
			
			let monthPositions = calculateMonthPositions()
			
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
	
	private func calculateMonthPositions() -> [MonthInfo] {
		var monthInfos: [MonthInfo] = []
		var currentMonth = -1
		var monthStartWeek = 0
		
		for (weekIndex, week) in completionYear.weeks.enumerated() {
			if let dayInCurrentYear = week.days.first(where: {
				let calendar = Calendar.current
				let year = calendar.component(.year, from: $0.date)
				return year == completionYear.year
			}) {
				let calendar = Calendar.current
				let month = calendar.component(.month, from: dayInCurrentYear.date) - 1
				
				if month != currentMonth {
					if currentMonth != -1 {
						let weekCount = weekIndex - monthStartWeek
						let width = CGFloat(weekCount) * (daySize + daySpacing)
						
						monthInfos.append(MonthInfo(
							monthIndex: currentMonth,
							width: width,
							startWeek: monthStartWeek,
							endWeek: weekIndex - 1
						))
					}
					
					currentMonth = month
					monthStartWeek = weekIndex
				}
			}
		}
		
		if currentMonth != -1 {
			let weekCount = completionYear.weeks.count - monthStartWeek
			let width = CGFloat(weekCount) * (daySize + daySpacing)
			
			monthInfos.append(MonthInfo(
				monthIndex: currentMonth,
				width: width,
				startWeek: monthStartWeek,
				endWeek: completionYear.weeks.count - 1
			))
		}
		
		return monthInfos
	}
	
	
	private struct MonthInfo {
		let monthIndex: Int
		let width: CGFloat
		let startWeek: Int
		let endWeek: Int
	}
}
