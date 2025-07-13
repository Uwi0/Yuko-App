import SwiftUI

struct WeekHeaderView: View {
	@StateObject var weekStore = WeekStore()
	@State private var snappedItem = 0.0
	@State private var draggingItem = 0.0
	
	var body: some View {
		VStack {
			WeakHeaderContent()
		}
	}
	
	@ViewBuilder
	func WeakHeaderContent() -> some View {
		ZStack {
			ForEach(weekStore.allWeeks) { week in
				WeekOfDaysView(
					week: week,
					onSelectedDayOfWeek: { date in weekStore.currentDate = date}
				)
					.offset(x: myXOffset(week.id), y: 0)
					.zIndex(1.0 - abs(distance(week.id)) * 0.1)
					.padding(.horizontal, 20)
			}
		}
		.frame(maxHeight:.infinity , alignment : .top)
		.padding(.top,50)
		.gesture(dragGesture())
	}
	
	private func dragGesture() -> some Gesture {
		DragGesture()
			.onChanged { value in
				draggingItem = snappedItem + value.translation.width / 1500
			}
			.onEnded { value in
				withAnimation(.smooth) {
					if value.predictedEndTranslation.width > 0 {
						draggingItem = snappedItem + 1
					} else {
						draggingItem = snappedItem - 1
					}
					snappedItem = draggingItem
					weekStore.update(index: Int(snappedItem))
				}
			}
	}

	
	func distance(_ item: Int) -> Double {
		return (draggingItem - Double(item)).remainder(dividingBy: Double(weekStore.allWeeks.count))
	}
	
	func myXOffset(_ item: Int) -> Double {
		let angle = Double.pi * 2 / Double(weekStore.allWeeks.count) * distance(item)
		return sin(angle) * 200
	}
	
}

#Preview {
	WeekHeaderView()
}
