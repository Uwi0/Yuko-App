import SwiftUI
import Shared

struct GoodHabitMetricsView: View {
	
	let habit: GoodHabitModel
	
	var body: some View {
		VStack(spacing: 12) {
			HStack(alignment: .center, spacing: 8) {
				MetricDivider()
				MetricItem(title: "\(habit.totalComplete)", description: "Times")
				MetricDivider()
				MetricItem(title: "\(habit.missedCount)", description: "Missed")
				MetricDivider()
				MetricItem(title: "\(habit.completionThisMonth)", description: "Month")
				MetricDivider()
				MetricItem(title: "\(habit.completionThisMonth)", description: "Total")
				MetricDivider()
				
			}
			Divider()
			HStack(spacing: 8) {
				MetricStreak(title: "\(habit.bestStreak)", description: "Best Streak")
				MetricStreak(title: "\(habit.currentStreak)", description: "Current Streak")
			}
		}
		.padding(4)
	}
	
	@ViewBuilder
	private func MetricItem(title: String,description: String) -> some View {
		VStack(spacing: 8) {
			Text(title)
				.font(Typography.titleMedium)
			Text(description)
				.font(Typography.bodyMedium)
		}
		.frame(maxWidth: .infinity)
	}
	
	@ViewBuilder
	private func MetricStreak(title: String, description: String) -> some View {
		VStack(alignment: .leading, spacing: 8) {
			Text(title)
				.font(Typography.titleLarge)
			Text(description)
				.font(Typography.bodyMedium)
		}
		.padding(.vertical, 10)
		.padding(.horizontal, 16)
		.frame(maxWidth: .infinity, alignment: .leading)
		.background(ColorTheme.surface)
		.overlay(RoundedRectangle(cornerRadius: ShapeStyles.medium)
			.stroke(ColorTheme.onSurface, lineWidth: SizeConstant.BorderWidth))
	}

	
	@ViewBuilder
	private func MetricDivider() -> some View {
		Divider()
			.frame(height: 48)
	}
}

#Preview {
	GoodHabitMetricsView(habit: dummyGoodHabit)
}
