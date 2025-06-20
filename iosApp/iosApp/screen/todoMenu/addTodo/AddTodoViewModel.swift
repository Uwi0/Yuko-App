import Foundation
import Shared
import Combine
import KMPNativeCoroutinesCombine

final class AddTodoViewModel: ObservableObject {
	
	private let viewModel: AddTodoViewModelKt = Koin.shared.get()
	private let effectSubject = PassthroughSubject<AddTodoEffect, Never>()
	private var effectCancellable: AnyCancellable?
	
	var effectPublsher: AnyPublisher<AddTodoEffect, Never> {
		effectSubject.eraseToAnyPublisher()
	}
	
	
	func initData() {
		observeEffect()
	}
	
	func handle(event: AddTodoEvent) {
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
