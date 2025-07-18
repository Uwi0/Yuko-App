import SwiftUI
import Shared

struct CompletionDayView: View {
	
	let day: CompletionDayModel
	let size: CGFloat
	let onTap: () -> Void
	
	var body: some View {
		Rectangle()
			.fill(color(day.level))
			.frame(width: size, height: size)
			.cornerRadius(2)
			.onTapGesture {
				onTap()
			}
	}
	
	private func color(_ level: Int) -> Color {
		switch level {
		case 0: return Color.gray.opacity(0.1)
		case 1: return Color.green.opacity(0.3)
		case 2: return Color.green.opacity(0.5)
		case 3: return Color.green.opacity(0.7)
		case 4: return Color.green
		default: return Color.gray.opacity(0.1)
		}
	}
}
