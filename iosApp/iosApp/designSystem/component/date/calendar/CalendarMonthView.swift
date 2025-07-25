import SwiftUI
import Shared

struct CalendarMonthView: View {
	
	var currentDate: Date
	var months: [MonthModel]
	var canScrolledRight: Bool
	var canScrolledLeft: Bool
	let onUpdatedIndex: (Int32) -> Void
	
	@State private var snappedItem = 0.0
	@State private var draggingItem = 0.0
	@State private var isUpdating = false
	
	var body: some View {
		VStack {
			HeaderContentView()
			BodyContentView()
		}
	}
	
	@ViewBuilder
	private func HeaderContentView() -> some View {
		let yearAndMonth = dateToString(date: currentDate, format: "yyyy MMM")
		HStack(spacing: 16) {
			Text(yearAndMonth)
			Spacer()
			ButtonChangeMonthView(
				image: "chevron.left",
				disabled: !canScrolledLeft,
				onClick: { index in
					let newIndex = normalizeIndex(index + 1)
					return Double(newIndex)
				}
			)
			ButtonChangeMonthView(
				image: "chevron.right",
				disabled: !canScrolledRight,
				onClick: { index in
					let newIndex = normalizeIndex(index - 1)
					return Double(newIndex)
				}
			)
		}
	}
	
	@ViewBuilder
	private func ButtonChangeMonthView(
		image: String,
		disabled: Bool,
		onClick: @escaping (Int) -> Double
	) -> some View {
		
		let color = disabled ? ColorTheme.outline : ColorTheme.primary
		
		Button {
			guard !isUpdating else { return }
			
			let currentIndex = Int(snappedItem)
			let moveIndex = onClick(currentIndex)
			
			updateCalendar(to: moveIndex)
			
		} label : {
			Image(systemName: image)
				.resizable()
				.scaledToFit()
				.frame(width: 24, height: 24)
				.padding(10)
				.foregroundStyle(color)
		}
		.disabled(isUpdating || disabled)
	}
	
	@ViewBuilder
	private func BodyContentView() -> some View {
		GeometryReader { geo in
			ZStack {
				ForEach(Array(months.enumerated()), id: \.offset) { index, month in
					MonthsView(month: month)
						.offset(x: myXOffset(index, radius: geo.size.width * 0.1))
						.scaleEffect(1.0 - abs(distance(index)) * 0.2)
						.opacity(1.0 - abs(distance(index)) * 0.3)
						.zIndex(1.0 - abs(distance(index)) * 0.1)
				}
			}
			.animation(.easeInOut(duration: 0.5), value: draggingItem)
		}
		.frame(height: 220)
		.gesture(dragGesture())
	}
	
	private func dragGesture() -> some Gesture {
		DragGesture()
			.onChanged { value in
				guard !isUpdating else { return }
				
				let translation = value.translation.width
				if (translation > 0 && !canScrolledLeft) || (translation < 0 && !canScrolledRight) {
					return
				}
				
				draggingItem = snappedItem + translation / 1500
			}
			.onEnded { value in
				guard !isUpdating else { return }
				
				let translation = value.predictedEndTranslation.width
				if (translation > 0 && !canScrolledLeft) || (translation < 0 && !canScrolledRight) {
					withAnimation(.smooth(duration: 0.2)) {
						draggingItem = snappedItem
					}
					return
				}
				
				let predictedIndex = translation > 0
				? normalizeIndex(Int(snappedItem) + 1)
				: normalizeIndex(Int(snappedItem) - 1)
				
				updateCalendar(to: Double(predictedIndex))
			}
	}
	
	private func updateCalendar(to newIndex: Double) {
		isUpdating = true
		
		withAnimation(.smooth(duration: 0.3)) {
			draggingItem = newIndex
			snappedItem = newIndex
		} completion: {
			
			onUpdatedIndex(Int32(newIndex))
			
			DispatchQueue.main.asyncAfter(deadline: .now() + 0.05) {
				isUpdating = false
			}
		}
	}
	
	private func normalizeIndex(_ index: Int) -> Int {
		let count = 3
		let normalized = index % count
		return normalized < 0 ? normalized + count : normalized
	}
	
	private func distance(_ item: Int) -> Double {
		let rawDistance = draggingItem - Double(item)
		let count = Double(months.count)
		let normalizedDistance = rawDistance.remainder(dividingBy: count)
		
		if normalizedDistance > count / 2 {
			return normalizedDistance - count
		} else if normalizedDistance < -count / 2 {
			return normalizedDistance + count
		}
		return normalizedDistance
	}
	
	private func myXOffset(_ item: Int, radius: Double) -> Double {
		let angle = Double.pi * 2 / Double(months.count) * distance(item)
		return sin(angle) * radius
	}
}
