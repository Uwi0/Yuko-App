import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

@Observable
final class AddHabitViewModel {
	
	var state: AddHabitState = .companion.default()
	var effectPublihser: AnyPublisher<AddHabitEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	private let viewModel: AddHabitViewModelKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<AddHabitEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	func initData() {
		observeState()
		observeEffect()
	}
	
	func handle(event: AddHabitEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeState() {
		let publisher = createPublisher(for: viewModel.uiStateFlow)
		stateCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("comletion \(completion)")
			} receiveValue: { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	private func observeEffect() {
		let publsiher = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publsiher
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

extension AddHabitState {
	public var targetFrequency: Int { Int(self.targetFrequencyKt)}
}
