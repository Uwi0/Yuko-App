import SwiftUI
import Shared

struct ToolTipView: View {
	let day: CompletionDayModel
	let isVisible: Bool
	
	var body: some View {
		if isVisible {
			VStack(alignment: .leading, spacing: 4) {
				Text(date)
					.font(Typography.titleMedium)
				
				Text("\(count) completed")
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
	
	private var date: String {
		switch onEnum(of: day) {
		case let .day(date): return formatDate(date.date)
		case .empty: return "No data"
		}
	}
	
	private var count: String {
		switch onEnum(of: day) {
		case let .day(day): return"\(day.count)"
		case .empty: return "0"
		}
	}
	
	private func formatDate(_ date: Date) -> String {
		let formatter = DateFormatter()
		formatter.dateStyle = .medium
		return formatter.string(from: date)
	}
}

