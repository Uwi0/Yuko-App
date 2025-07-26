import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

@Observable
final class GoodHabitViewModel {
	
	var state: GoodHabitState = .companion.default()
	var efectPublihser: AnyPublisher<GoodHabitEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	private let viewModel: GoodHabitViewModelKt = Koin.shared.get()
	private var effectSubject = PassthroughSubject<GoodHabitEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	func initData(id: Int64) {
		viewModel.doInitData(habitId: id)
		observeState()
		observeEffect()
	}
	
	func handle(event: GoodHabitEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeState() {
		let publisher = createPublisher(for: viewModel.uiStateFlow)
		stateCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	private func observeEffect() {
		let publisher = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] effect in
				guard let effect else { return }
				self?.effectSubject.send(effect)
			}
	}
	
	deinit {
		viewModel.onCleared()
		stateCancellable?.cancel()
		effectCancellable?.cancel()
	}
}

extension GoodHabitState {
	public var formattedDate: Date { self.currentDate.toDate() }
	public var weeksValue: [Double] { self.completionWeeks.map{ Double(truncating: $0)} }
}
