import Foundation

class WeekStore : ObservableObject {
		@Published var allWeeks : [WeekValue] = []
		
		@Published var currentDate : Date = Date()
		
		@Published var currentIndex : Int = 0
		@Published var indexToUpdate : Int = 0
		
		@Published var currentWeek: [Date] = []
		@Published var nextWeek : [Date] = []
		@Published var previousWeek : [Date] = []
		
		init() {
				fetchCurrentWeek()
				fetchPreviousNextWeek()
				appendAll()
		}
		
		func appendAll() {
				var  newWeek = WeekValue(id: 0, date: currentWeek)
				allWeeks.append(newWeek)
				
				newWeek = WeekValue(id: 2, date: nextWeek)
				allWeeks.append(newWeek)
				
				newWeek = WeekValue(id: 1, date: previousWeek)
				allWeeks.append(newWeek)
		}
		
		func update(index : Int) {
				var value : Int = 0
				if index < currentIndex {
						value = 1
						if indexToUpdate ==  2 {
								indexToUpdate = 0
						} else {
								indexToUpdate = indexToUpdate + 1
						}
				} else {
						value = -1
						if indexToUpdate ==  0{
								indexToUpdate = 2
						} else {
								indexToUpdate = indexToUpdate - 1
						}
				}
				currentIndex = index
				addWeek(index: indexToUpdate, value: value)
		}
		
		func addWeek(index: Int, value: Int) {
				allWeeks[index].date.removeAll()
				var calendar = Calendar(identifier: .gregorian)
				let today = Calendar.current.date(byAdding: .day, value: 7 * value , to: self.currentDate)!
				self.currentDate = today
				
				calendar.firstWeekday = 7
				let startOfWeek = calendar.date(from: calendar.dateComponents([.yearForWeekOfYear, .weekOfYear], from: today))!
				
				(1...7).forEach{ day in
						if let weekday = calendar.date(byAdding: .day, value: day, to: startOfWeek){
								allWeeks[index].date.append(weekday)
						}
				}
		}
		
		func isToday(date:Date)->Bool{
				let calendar = Calendar.current
				return calendar.isDate(currentDate, inSameDayAs: date)
		}
		
		
		func dateToString(date: Date,format: String)->String{
				let formatter = DateFormatter()
				formatter.dateFormat = format
				return formatter.string(from: date)
		}
		
		func fetchCurrentWeek(){
				let today = currentDate
				var calendar = Calendar(identifier: .gregorian)
		
				calendar.firstWeekday = 7
				let startOfWeek = calendar.date(from: calendar.dateComponents([.yearForWeekOfYear, .weekOfYear], from: today))!
				
				(1...7).forEach{ day in
						if let weekday = calendar.date(byAdding: .day, value: day, to: startOfWeek){
								currentWeek.append(weekday)
						}
				}
		}
		
		func fetchPreviousNextWeek(){
				nextWeek.removeAll()
				
				let nextWeekToday = Calendar.current.date(byAdding: .day, value: 7, to: currentDate )!
				
				var calendar = Calendar(identifier: .gregorian)
				calendar.firstWeekday = 7
				
				let startOfWeekNext = calendar.date(from: calendar.dateComponents([.yearForWeekOfYear, .weekOfYear], from: nextWeekToday))!
				
				(1...7).forEach{ day in
						if let weekday = calendar.date(byAdding: .day, value: day, to: startOfWeekNext){
								nextWeek.append(weekday)
						}
						
				}
				
				previousWeek.removeAll()
				let previousWeekToday = Calendar.current.date(byAdding: .day, value: -7, to: currentDate)!
				
				let startOfWeekPrev = calendar.date(from: calendar.dateComponents([.yearForWeekOfYear, .weekOfYear], from: previousWeekToday))!
				
				(1...7).forEach{ day in
						if let weekday = calendar.date(byAdding: .day, value: day, to: startOfWeekPrev){
								previousWeek.append(weekday)
						}
				}
		}
}
