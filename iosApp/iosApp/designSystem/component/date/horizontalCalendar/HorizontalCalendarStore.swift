import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class HorizontalCalendarStore: ObservableObject {
	@Published var allWeeks: [WeekModel] = []
	@Published var currentDate: Date = Date()
	@Published var currentMonth: Date = Date()
	var effectPublsiher: AnyPublisher<HorizontalCalendarEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	private let store: HorizontalCalendarStoreKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<HorizontalCalendarEffect, Never>()
	private var allWeeksCancellable: AnyCancellable?
	private var currentDateCancellable: AnyCancellable?
	private var currentMonthCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	func initData() {
		store.doInitData()
		observeAllWeeks()
		observeCurrentDate()
		observeCurrentMonth()
		observerEffect()
	}
	
	func handle(event: HorizontalCalendarEvent){
		store.handleEvent(event: event)
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
	
	private func observerEffect() {
		let publisher = createPublisher(for: store.storeEffect)
		effectCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] effect in
				self?.effectSubject.send(effect)
			}
	}
	
	deinit {
		allWeeksCancellable?.cancel()
		currentDateCancellable?.cancel()
		currentMonthCancellable?.cancel()
		effectCancellable?.cancel()
		store.cancel()
	}
}

extension WeekModel: @retroactive Identifiable {
	public var id: Int { Int(self.weekId) }
	public var dates: [Date] { self.weekDates.map { $0.toDate() }}
}
