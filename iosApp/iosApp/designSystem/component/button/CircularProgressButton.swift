import SwiftUI

struct CircularProgressButton: View {
	@Binding var progress: Int
	let target: Int
	
	private var percentage: Double {
		Double(progress) / Double(target)
	}
	
	private var isComplete: Bool {
		progress >= target
	}
	
	var body: some View {
		ZStack {
			BackgroundCircle()
			CircularProgressIndicator()
			ContentView()
		}
		.frame(width: 32, height: 32)
		.onTapGesture {
			if !isComplete {
				progress += 1
			}
		}
		.contentShape(Circle())
	}
	
	@ViewBuilder
	private func BackgroundCircle() -> some View {
		Circle()
			.stroke(Color.gray.opacity(0.3), lineWidth: 4)
	}
	
	@ViewBuilder
	private func CircularProgressIndicator() -> some View {
		Circle()
			.trim(from: 0, to: percentage)
			.stroke(
				ColorTheme.primary,
				style: StrokeStyle(lineWidth: 4, lineCap: .round)
			)
			.rotationEffect(.degrees(-90))
			.animation(.easeInOut, value: progress)
	}
	
	@ViewBuilder
	private func ContentView() -> some View {
		if isComplete {
			Image(systemName: "checkmark")
				.font(.system(size: 12, weight: .bold))
				.foregroundColor(ColorTheme.primary)
		} else {
			Text("\(progress)")
				.font(.system(size: 10, weight: .semibold))
				.foregroundColor(ColorTheme.primary)
		}
	}
}

#Preview {
	@Previewable @State var progress = 0
	let target = 5
	CircularProgressButton(progress: $progress, target: target)
}
