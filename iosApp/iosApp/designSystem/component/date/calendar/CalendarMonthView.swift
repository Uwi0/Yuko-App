import SwiftUI

struct CalendarMonthView: View {
	
	@StateObject private var store = CalendarMonthStore()
	@State private var snappedItem = 0.0
	@State private var draggingItem = 0.0
	
	var body: some View {
		VStack {
			HeaderContentView()
			BodyContentView()
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
		onClick: @escaping (Double) -> Double
	) -> some View {
		Button {
			withAnimation(.smooth) {
				let moveIndex = onClick(snappedItem)
				snappedItem = moveIndex
				draggingItem = snappedItem
				store.update(index: Int32(moveIndex))
			}
		} label : {
			Image(systemName: image)
				.resizable()
				.scaledToFit()
				.frame(width: 24, height: 24)
				.padding(10)
				.foregroundStyle(ColorTheme.primary)
		}
	}
	
	@ViewBuilder
	private func BodyContentView() -> some View {
		GeometryReader { geo in
			ZStack {
				ForEach(store.allMonths) { month in
					MonthsView(month: month)
						.offset(x: myXOffset(month.id, radius: geo.size.width * 0.1))
						.scaleEffect(1.0 - abs(distance(month.id)) * 0.2)
						.opacity(1.0 - abs(distance(month.id)) * 0.3)
						.zIndex(1.0 - abs(distance(month.id)) * 0.1)
				}
			}
		}
		.gesture(dragGesture())
	}
	
	private func dragGesture() -> some Gesture {
		DragGesture()
			.onChanged { value in
				draggingItem = snappedItem + value.translation.width / 1500
			}
			.onEnded { value in
				withAnimation(.smooth(duration: 0.3)) {
					if value.predictedEndTranslation.width > 0 {
						draggingItem = snappedItem - 1
					} else {
						draggingItem = snappedItem + 1
					}
					snappedItem = draggingItem
				} completion: {
					store.update(index: Int32(snappedItem))
				}
			}
	}
	
	private func distance(_ item: Int) -> Double {
		return (draggingItem - Double(item)).remainder(dividingBy: Double(store.allMonths.count))
	}
	
	private func myXOffset(_ item: Int, radius: Double) -> Double {
		let angle = Double.pi * 2 / Double(store.allMonths.count) * distance(item)
		return sin(angle) * radius
	}
}

#Preview {
	CalendarMonthView()
		.padding(16)
		.frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
}
