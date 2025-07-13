import SwiftUI

struct WeekItemView: View {
	
	let weekValue: WeekValue
	let index: Int
	
	var body: some View {
		let formatter: (String) -> String = { format in
			dateToString(date: weekValue.date[index], format: format)
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
}

