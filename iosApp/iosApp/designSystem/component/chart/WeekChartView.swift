import SwiftUI
import Shared

struct WeekChartView: View {
	let data: [Double]
	private let weekDays: [String] = WeekDays.shared.shortNames
	private let linePercentage: [Double] = [1.0, 0.75, 0.5, 0.25, 0.0]
	
	var body: some View {
		HStack(alignment: .bottom, spacing: 8) {
			ChartPercentage()
			
			Rectangle()
				.fill(Color.gray.opacity(0.5))
				.frame(width: 1)
				.frame(maxHeight: .infinity)
			
			HStack(alignment: .bottom, spacing: 16) {
				ForEach(Array(data.enumerated()), id: \.offset) { index, value in
					BarItemView(index: index, value: value)
				}
			}
			.animation(
				data.isEmpty ? .none : .spring(response: 0.5, dampingFraction: 0.5, blendDuration: 0.2),
				value: data
			)
			.frame(maxWidth: .infinity)
		}
		.padding()
		.frame(maxHeight: 260)
	}
	
	@ViewBuilder
	private func ChartPercentage() -> some View {
		VStack(alignment: .trailing, spacing: 0) {
			ForEach(linePercentage, id: \.self) { percent in
				Text("\(Int(percent * 100))%")
					.font(.caption2)
					.frame(height: 50)
					.offset(y: percent == 0 ? 5 : 0)
			}
		}
		.frame(width: 35)
	}
	
	@ViewBuilder
	private func BarItemView(index: Int, value: Double) -> some View {
		VStack(spacing: 8) {
			ZStack(alignment: .bottom) {
				Rectangle()
					.fill(Color.clear)
					.frame(maxWidth: .infinity, maxHeight: 220)
				
				Rectangle()
					.fill(Color.pink.opacity(0.6))
					.frame(maxWidth: .infinity, maxHeight: max(CGFloat(value) * 200, 1))
					.cornerRadius(4)
			}
			.clipped()
			
			Text(weekDays[index])
				.font(Typography.bodySmall)
		}
	}
}

#Preview {
	let dummyWeekData: [Double] = [
		0.3,
		0.6,
		0.8,
		0.2,
		0.5,
		0.7,
		1
	]
	WeekChartView(data: dummyWeekData)
}
