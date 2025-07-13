import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class HorizontalCalendarStore: ObservableObject {
	@Published var allWeeks: [WeekModel] = []
	@Published var currentDate: Date = Date()
	@Published var currentMonth: Date = Date()
	
	private let store = HorizontalCalendarStoreKt()
	private var allWeeksCancellable: AnyCancellable?
	private var currentDateCancellable: AnyCancellable?
	private var currentMonthCancellable: AnyCancellable?
	
	func initData() {
		store.doInitData()
		observeAllWeeks()
		observeCurrentDate()
		observeCurrentMonth()
	}
	
	func update(index: Int) {
		store.update(index: Int32(index))
	}
	
	private func observeAllWeeks() {
		let publisher = createPublisher(for: store.allWeeksFlow)
		allWeeksCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] weeks in
				self?.allWeeks = weeks
			}
	}
	
	private func observeCurrentDate() {
		let publisher = createPublisher(for: store.currentDateFlow)
		currentDateCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] date in
				self?.currentDate = date.toDate()
			}
	}
	
	private func observeCurrentMonth() {
		let publisher = createPublisher(for: store.currentMonthFlow)
		currentMonthCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] date in
				self?.currentMonth = date.toDate()
			}
	}
	
	deinit {
		allWeeksCancellable?.cancel()
		currentDateCancellable?.cancel()
		currentMonthCancellable?.cancel()
	}
}

extension WeekModel: @retroactive Identifiable {
	public var id: Int { Int(self.weekId) }
	public var dates: [Date] { self.weekDates.map { $0.toDate() }}
}
