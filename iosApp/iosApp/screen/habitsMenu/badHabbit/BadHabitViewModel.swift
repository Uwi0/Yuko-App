import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class BadHabitViewModel: ObservableObject {
	
	@Published var state: BadHabitState = .companion.default()
	var effectPublisher: AnyPublisher<BadHabitEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	private let viewModel: BadHabitViewModelKt = Koin.shared.get()
	private var effectSubject = PassthroughSubject<BadHabitEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	func initData(id: Int64){
		viewModel.doInitData(id: id)
		observeState()
		observeEffect()
	}
	
	func handle(event: BadHabitEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeState() {
		let publisher = createPublisher(for: viewModel.uiStateFlow)
		stateCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("compleiton \(completion)")
			} receiveValue: { [weak self] state in
				guard let state else { return }
				self?.state = state
			}
	}
	
	private func observeEffect() {
		let publihser = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publihser
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("compleiton \(completion)")
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
