import SwiftUI

struct HorizontalDateContentView: View {
	
	@StateObject var viewModel = ContentViewModel()
	
	var body: some View {
		List {
			VStack {
				HorizontalCalendarView(viewModel: viewModel.calendarViewModel)
					.frame(height: viewModel.calendarHeight ?? 200.0)
				Button {
					viewModel.highlightDaysOnCalendar()
				} label: {
					Text("Highlight some days")
						.foregroundStyle(Color.blue)
				}
				.padding()
				.buttonStyle(.plain)
				
			}
			.listRowSeparator(.hidden)
		}
		.listStyle(.plain)
	}
}

#Preview {
	HorizontalDateContentView()
}
