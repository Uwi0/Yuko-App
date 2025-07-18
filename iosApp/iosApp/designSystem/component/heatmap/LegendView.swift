import SwiftUI

struct LegendView: View {
	var body: some View {
		HStack {
			Spacer()
			Text("Less")
				.font(Typography.titleMedium)
			
			HStack(spacing: 4) {
				ForEach(0..<5, id: \.self) { level in
					Rectangle()
						.fill(color(level))
						.frame(width: 10, height: 10)
						.cornerRadius(2)
				}
			}
			
			Text("More")
				.font(Typography.titleMedium)
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

#Preview {
	LegendView()
}
