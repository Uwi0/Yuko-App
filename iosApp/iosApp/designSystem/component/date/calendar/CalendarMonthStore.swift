import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class CalendarMonthStore: ObservableObject {
	
	@Published var allMonths: [MonthModel] = []
	@Published var currentDate: Date = Date()
	
	private var store = CalendarStoreKt()
	private var allMonthsCancelable: AnyCancellable?
	private var currentDateCancelable: AnyCancellable?
	private var currentIndexCancelable: AnyCancellable?
	
	func initData() {
		store.doInitData()
		observeAllMonths()
		observeCurrentDate()
	}
	
	func update(index: Int32) {
		store.update(index: index)
	}
	
	private func observeAllMonths() {
		let publisher = createPublisher(for: store.allMonthsFlow)
		allMonthsCancelable = publisher.receive(on: DispatchQueue.main)
			.debounce(for: .milliseconds(100), scheduler: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] allMonths in
				self?.allMonths = allMonths
			}
	}
	
	private func observeCurrentDate() {
		let publisher = createPublisher(for: store.currentDateFlow)
		currentDateCancelable = publisher.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] currentDate in
				self?.currentDate = currentDate.toDate()
			}
	}
	
	deinit {
		allMonthsCancelable?.cancel()
		currentDateCancelable?.cancel()
		currentIndexCancelable?.cancel()
	}
}

extension MonthModel: @retroactive Identifiable {
	public var id: Int { Int(self.monthId) }
}

extension WeekOfMonthModel: @retroactive Identifiable {
	public var id: Int { Int(self.weekId) }
}
