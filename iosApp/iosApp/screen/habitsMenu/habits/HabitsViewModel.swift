import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class HabitsViewModel: ObservableObject {
	
	@Published var state: HabitsState = .companion.default()
	var effectPublisher: AnyPublisher<HabitsEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	private let viewModel: HabitsViewModelKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<HabitsEffect, Never>()
	private var stateCancellable: AnyCancellable?
	private var effectCancellable: AnyCancellable?
	
	func initData() {
		viewModel.doInitData()
		observeState()
		observeEffect()
	}
	
	func handle(event: HabitsEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeState() {
		let publisher = createPublisher(for: viewModel.uiStateFlow)
		stateCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] state in
				guard let state else {
					return
				}
				self?.state = state
			}
	}
	
	private func observeEffect() {
		let publihser = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publihser
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] effect in
				guard let effect else {
					return
				}
				self?.effectSubject.send(effect)
			}
	}
	
	deinit {
		viewModel.onCleared()
		stateCancellable?.cancel()
		effectCancellable?.cancel()
	}
}
