import SwiftUI
import Shared

struct ToolTipView: View {
	let day: CompletionDayModel?
	let isVisible: Bool
	
	var body: some View {
		if isVisible, let day = day {
			VStack(alignment: .leading, spacing: 4) {
				Text(formatDate(day.date))
					.font(Typography.titleMedium)
				
				Text("\(day.count) completed")
					.font(Typography.bodyMedium)
					.foregroundColor(ColorTheme.outline)
			}
			.padding(.horizontal, 8)
			.padding(.vertical, 6)
			.background(Color.black.opacity(0.8))
			.foregroundColor(.white)
			.cornerRadius(6)
			.transition(.opacity)
		}
	}
	
	private func formatDate(_ date: Date) -> String {
		let formatter = DateFormatter()
		formatter.dateStyle = .medium
		return formatter.string(from: date)
	}
}

