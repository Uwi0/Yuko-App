import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class TodoViewModel: ObservableObject {
	
	var effectPublihser: AnyPublisher<TodoEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	private let viewModel: TodoViewModelKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<TodoEffect, Never>()
	private var effectCancellable: AnyCancellable?
	
	func initData() {
		observeEffect()
	}
	
	func handle(event: TodoEvent) {
		viewModel.handleEvent(event: event)
	}
	
	private func observeEffect() {
		let publisher = createPublisher(for: viewModel.uiEffect)
		effectCancellable = publisher
			.receive(on: DispatchQueue.main)
			.sink { completion in
				print("completion \(completion)")
			} receiveValue: { [weak self] effect in
				self?.effectSubject.send(effect)
			}
	}
	
	deinit {
		effectCancellable?.cancel()
	}
}
