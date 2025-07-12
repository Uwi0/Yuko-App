import SwiftUI

struct WeekHeaderView: View {
	@StateObject var weekStore = WeekStore()
	@State private var snappedItem = 0.0
	@State private var draggingItem = 0.0
	
	var body: some View {
		ZStack {
			ForEach(weekStore.allWeeks) { week in
				VStack{
					WeekOfDaysView(week: week)
				}
				.offset(x: myXOffset(week.id), y: 0)
				.zIndex(1.0 - abs(distance(week.id)) * 0.1)
				.padding(.horizontal, 20)
			}
		}
		.frame(maxHeight:.infinity , alignment : .top)
		.padding(.top,50)
		.gesture(
			DragGesture()
				.onChanged { value in
					draggingItem = snappedItem + value.translation.width / 1000
				}
				.onEnded { value in
					withAnimation {
						if value.predictedEndTranslation.width > 0 {
							draggingItem = snappedItem + 1
						} else {
							draggingItem = snappedItem - 1
						}
						snappedItem = draggingItem
						
						weekStore.update(index: Int(snappedItem))
					}
				}
		)
	}
	
	@ViewBuilder
	private func WeekOfDaysView(week: WeekValue) -> some View {
		HStack {
			ForEach(0..<7) { index in
				WeekItemView(week: week, index: index)
				.onTapGesture {
					weekStore.currentDate = week.date[index]
				}
			}
		}
		.frame(width: UIScreen.main.bounds.width)
		.background(
			Rectangle()
				.fill(.white)
		)
	}
	
	@ViewBuilder
	private func WeekItemView(week: WeekValue, index: Int) -> some View {
		
		let formatter: (String) -> String = { format in
			weekStore.dateToString(date: week.date[index], format: format)
		}
		
		let dayFormatter = formatter("EEE")
		let dateFormatter = formatter("d")
		
		VStack(spacing: 20) {
			Text(dayFormatter)
				.font(.system(size:14))
				.fontWeight(.semibold)
				.frame(maxWidth:.infinity)
			
			Text(dateFormatter)
				.font(.system(size:14))
				.frame(maxWidth:.infinity)
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
