import SwiftUI

struct CalendarMonthView: View {
	
	@StateObject private var store = CalendarMonthStore()
	@State private var currentIndex: Int32 = 0
	
	var body: some View {
		VStack {
			HeaderContentView()
		}
		.task {
			store.initData()
		}
	}
	
	@ViewBuilder
	private func HeaderContentView() -> some View {
		let yearAndMonth = dateToString(date: store.currentDate, format: "yyyy MMM")
		HStack(spacing: 16) {
			Text(yearAndMonth)
			Spacer()
			ButtonChangeMonthView(
				image: "chevron.left",
				onClick: { index in index - 1 }
			)
			ButtonChangeMonthView(
				image: "chevron.right",
				onClick: { index in index + 1 }
			)
		}
	}
	
	@ViewBuilder
	private func ButtonChangeMonthView(
		image: String,
		onClick: @escaping (Int32) -> Int32
	) -> some View {
		Button {
			let movedIndex = onClick(currentIndex)
			self.currentIndex = movedIndex
			store.update(index: movedIndex)
		} label : {
			Image(systemName: image)
				.resizable()
				.scaledToFit()
				.frame(width: 24, height: 24)
				.padding(10)
				.foregroundStyle(ColorTheme.primary)
		}
	}
}

#Preview {
	CalendarMonthView()
		.padding(16)
		.frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
}
