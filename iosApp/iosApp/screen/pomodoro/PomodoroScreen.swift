import SwiftUI
import Shared

struct PomodoroScreen: View {
    
    @StateObject private var viewModel = PomodoroViewModel()
    @ObservedObject private var pomodoroTimerService = BackgroundTimerService()
    @ObservedObject private var countdownTimerService = TimerService()
    
    var body: some View {
        VStack {
            TopContentView()
            Spacer()
            TimeCountDownComponent()
            Spacer().frame(height: 48)
            StartButton()
            Spacer()
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 24)
        .onAppear {
            viewModel.initData()
        }
        .sheet(isPresented: Binding(
            get: { viewModel.uiState.showSheet},
            set: { shown in viewModel.handle(event: .ShowSheet(show: shown))})
        ){
            SetTimeSheet(
                focusDuration: Binding(
                    get: { viewModel.uiState.focusDuration },
                    set: { duration in viewModel.handle(event: .ChangeFocusTime(time: duration)) }),
                shortRestDuration: Binding(
                    get: { viewModel.uiState.shortRestDuration},
                    set: { duration in viewModel.handle(event: .ChangeShortRestTime(time: duration)) }),
                numberOfCyles: Binding(
                    get: { viewModel.uiState.numberOfCycles},
                    set: { cycle in viewModel.handle(event: .SetNumberOfCycles(number: cycle)) }),
                onDismiss: { viewModel.handle(event: .ShowSheet(show: false)) },
                onStart: { viewModel.handle(event: .SaveSettings() ) }
            )
            .presentationDetents([.height(480)])
        }
        .alert(
            isPresented: Binding(
                get: { viewModel.uiState.showAlert },
                set: { shown in viewModel.handle(event: .ShowAlert(shown: shown))}),
            
        ) {
            Alert(
                title: Text("Are you sure want to give up?"),
                message: Text("Yuko will sad if you do that"),
                primaryButton: .destructive(
                    Text("Confirm"),
                    action: {
                        viewModel.handle(event: .SaveProgress(isSuccess: false))
                    }
                ),
                secondaryButton: .cancel()
            )
        }
        .onChange(of: viewModel.uiEffect) {
            observe(effect: viewModel.uiEffect)
        }
    }
    
    @ViewBuilder
    private func TopContentView() -> some View {
        HStack {
            PointItem()
            Spacer()
        }
    }
    
    @ViewBuilder
    private func PointItem() -> some View {
        HStack(spacing: 8) {
            Image(systemName: "heart.fill")
            Text("\(viewModel.uiState.pointEarned)")
        }
    }
    
    @ViewBuilder
    private func TimeCountDownComponent() -> some View {
        HStack(alignment: .center, spacing: 8) {
            Image(systemName: "tag")
                .resizable()
                .frame(width: 16, height: 16)
            Text("Study: ")
                .font(Typography.titleSmall)
            Text(viewModel.uiState.pomodoroTime)
                .font(Typography.titleMedium)
            Image(systemName: "chevron.forward")
                .resizable()
                .scaledToFit()
                .frame(width: 16, height: 16)
        }
        .onTapGesture {
            viewModel.handle(event: .ShowSheet(show: true))
        }
    }
    
    @ViewBuilder
    private func StartButton() -> some View {
        let onCklick: () -> Void = {
            if viewModel.uiState.status == .focus {
                viewModel.handle(event: .StartPomodoro())
            } else if viewModel.uiState.status == .breakTime {
                
            } else {
                viewModel.handle(event: .CancelTimer())
            }
        }
        
        let title = switch viewModel.uiState.status {
        case .start: "Cancel"
        case .breakTime: "Start"
        case .countDown: "Cancel \(viewModel.uiState.countDownTime)"
        case .focus: "Break"
        }
        
        FilledButtonView(
            onClick: onCklick,
            content: { Text(title)}
        )
        .frame(width: 120)
    }
    
    private func observe(effect: PomodoroEffect?) {
        guard let effect = effect else { return }
        switch onEnum(of: effect) {
        case .startPomodoro(let value): startCountDownTimer(time: Int(value.time))
        case .showError(let effect): print("error \(effect.message)")
        case .cancelCountdown: stopCountDownTimer()
        case .cancelPomodoro: stopPomodoroTimer()
        }
        viewModel.uiEffect = nil
    }
    
    private func startCountDownTimer(time: Int) {
        countdownTimerService.remainingTime = 5
        countdownTimerService.startTimer(
            onTick: { time in
                viewModel.handle(event: .ChangeCountDownTime(time: String(time)))
            },
            onFinish: {
                startPomodoroTimer(time: time)
            }
        )
    }
    
    private func stopCountDownTimer() {
        countdownTimerService.stopTimer()
        countdownTimerService.remainingTime = 0
    }
    
    private func startPomodoroTimer(time: Int) {
        viewModel.handle(event: .ChangeStatus(status: .start))
        pomodoroTimerService.remainingTime = time
        pomodoroTimerService.startTimer(
            initialTime: time,
            onTick: { time in
                viewModel.handle(event: .ChangePomodoroTime(time: time.toFormatMinutesAndSeconds()))
            },
            onFinish: {
                viewModel.handle(event: .SaveProgress(isSuccess: true))
            }
        )
    }
    
    
    private func stopPomodoroTimer() {
        pomodoroTimerService.stopTimer()
        pomodoroTimerService.remainingTime = 0
    }
    
    
}

#Preview {
    PomodoroScreen()
}
