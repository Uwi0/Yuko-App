import SwiftUI
import ComposableArchitecture

struct MainMenuScreen: View {
    let store: StoreOf<MainMenuFeature>
    var body: some View {
        NavigationStack {
            WithViewStore(store, observe: { $0 }) { viewStore in
                HStack(spacing: 16) {
                    Button("Pomodoro") {
                        viewStore.send(.selectRoute(.pomodoro))
                    }
                    
                    Button("Note") {
                        viewStore.send(.selectRoute(.note))
                    }
                    
                    Button("Todo") {
                        viewStore.send(.selectRoute(.todo))
                    }
                    
                    Button("Habits") {
                        viewStore.send(.selectRoute(.habit))
                    }
                    
                    Button("Settings") {
                        viewStore.send(.selectRoute(.settings))
                    }
                }
                .navigationDestination(
                    isPresented: Binding(
                        get: { viewStore.route != nil},
                        set: { isPresented in
                            if !isPresented {
                                viewStore.send(.dismissRoute)
                            }
                        }
                    ),
                    destination: {
                        switch viewStore.route {
                        case .pomodoro: PomodoroScreen()
                        case .note: NotesScreen()
                        case .todo: TodosScreen()
                        case .habit: HabitsScreen()
                        case .settings: SettingsScreen()         
                        case .none: EmptyView()
                        }
                    }
                )
            }
        }
    }
}
