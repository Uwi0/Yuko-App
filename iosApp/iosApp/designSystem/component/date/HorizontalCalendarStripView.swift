import SwiftUI

struct HorizontalCalendarStripView: View {
	@StateObject var weekStore = HorizontalCalendarStore()
	@State private var snappedItem = 0.0
	@State private var draggingItem = 0.0
	
	var body: some View {
		VStack(alignment: .leading) {
			HeaderContentView()
			BodyCalendarView()
			Spacer().frame(height: 16)
		}
		.task {
			weekStore.initData()
		}
	}
	
	@ViewBuilder
	private func HeaderContentView() -> some View {
		let yearAndMonth = dateToString(date: weekStore.currentMonth, format: "yyyy MMM")
		HStack(spacing: 16) {
			Text(yearAndMonth)
			Spacer()
			ButtonDateActionView(
				image: "chevron.left",
				onClick: { index in index + 1 }
			)
			ButtonDateActionView(
				image: "chevron.right",
				onClick: { index in index - 1}
			)
		}
	}
	
	@ViewBuilder
	private func ButtonDateActionView(
		image: String,
		onClick: @escaping (Double) -> Double
	) -> some View {
		Button {
			withAnimation(.smooth) {
				let moveIndex = onClick(snappedItem)
				snappedItem = moveIndex
				draggingItem = snappedItem
				weekStore.update(index: Int(moveIndex))
			}
		} label: {
			Image(systemName: image)
				.resizable()
				.scaledToFit()
				.frame(width: 24, height: 24)
				.padding(10)
				.foregroundStyle(ColorTheme.primary)
		}
	}
	
	@ViewBuilder
	func BodyCalendarView() -> some View {
		GeometryReader { geo in
			ZStack {
				ForEach(weekStore.allWeeks) { week in
					WeekOfDaysView(
						week: week,
						onSelectedDayOfWeek: { date in weekStore.currentDate = date}
					)
					.offset(x: myXOffset(week.id, radius: geo.size.width * 0.1))
					.scaleEffect(1.0 - abs(distance(week.id)) * 0.2)
					.opacity(1.0 - abs(distance(week.id)) * 0.3)
					.zIndex(1.0 - abs(distance(week.id)) * 0.1)
				}
			}
			.frame(width: geo.size.width, alignment: .top)
		}
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
	
	func myXOffset(_ item: Int, radius: Double) -> Double {
		let angle = Double.pi * 2 / Double(weekStore.allWeeks.count) * distance(item)
		return sin(angle) * radius
	}
	
}


#Preview {
	VStack {
		HorizontalCalendarStripView()
			.frame(maxHeight: .infinity, alignment: .top)
		Spacer()
	}.padding(.horizontal, 16)
	
}
